/* $Id: server.c,v 1.2 2004/03/31 21:12:03 bja Exp $ 
 *
 * This software is hereby placed in the public domain. You are encouraged
 * to modify and distribute it in whatever way you find suitable. It is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.
 * 
 */

#include <sys/types.h>
#include <sys/socket.h>
#include <sys/poll.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include <pthread.h>
#include <errno.h>

#define DEFAULT_PORT		55555	/* default port to listen on */
#define DEFAULT_TIMEOUT       3000000	/* timeout in milliseconds */
#define MAX_THREADS		   32	/* maximum number of connections */

#define NO_CHDIR	            1	/* don't change dir to '/' */
#define NO_REDIR		    1	/* don't send output to /dev/null */

static pthread_t thread[MAX_THREADS];	/* maximum number of threads */
static int numthreads;			/* number of active threads */
static int server;			/* server socket */
static int done;			/* thread status */

static pthread_mutex_t thread_mutex = PTHREAD_MUTEX_INITIALIZER;

/* terminate
 *
 * Signal handler, terminates the server and exits cleanly.
 */
void terminate(int sig)
{
    fprintf(stderr, "server: terminated!\n");
    done = 1; /* notify threads */

    close(server);
    exit(0);
}

/* handler
 *
 * Handles incoming connections in a separate thread.
 *
 */
void *handler(void *arg)
{
    struct pollfd ufds;

    char buf[BUFSIZ+1];

    ufds.fd = *((int *) arg);
    ufds.events = POLLIN | POLLOUT;

    do {

	int status = poll(&ufds, 1, DEFAULT_TIMEOUT);
	int n;

	if (status < 0) {
	    fprintf(stderr,"server: poll failed: %s\n",
		    strerror(errno));
	} else if (status == 0) {
	    fprintf(stderr, "server: poll timed out!\n");
	}

	/* see if there is any data available */

	if (ufds.revents & POLLIN) {
	    if ((n = recv(ufds.fd, buf, BUFSIZ, 0)) < 0) {
		fprintf(stderr,"server: unable to recv: %s\n",
			strerror(errno));
	    } else {
		buf[n] = 0;
		fprintf(stderr, "--> %s\n", buf);
		ufds.events |= POLLOUT;
	    }
	} 
	
	/* see if we can send data without blocking */

	if (ufds.revents & POLLOUT) {
	    if ((n = send(ufds.fd, buf, strlen(buf), 0)) < 0) {
		fprintf(stderr,"server: unable to send: %s\n",
			strerror(errno));
	    } else if (n < strlen(buf)) {
		fprintf(stderr,"server: send was incomplete: %s\n",
			strerror(errno));
	    } else {
		ufds.events = POLLIN;
	    }
	} 

    } while (!done && buf[0] != EOF);

    pthread_mutex_lock(&thread_mutex);

    if (--numthreads < 0) {
	numthreads = 0;
	fprintf(stderr, "server: this should never happen!");
    }

    pthread_mutex_unlock(&thread_mutex);

    /* close socket */

    close(ufds.fd);

    fprintf(stderr, "server: thread terminated\n");

    pthread_exit(NULL);

    return 0;
}

/* main
 *
 *
 */
int main(int argc, char **argv)
{
    struct sockaddr_in l_addr; /* local address */
    struct sockaddr_in r_addr; /* remote address */

    in_port_t port = DEFAULT_PORT;

    int client;		/* client socket */
    int size;		/* size of address */

    /* parse command line arguments */

    if (argc > 1) {
	int val = atoi(argv[1]);
	if (val >= 0) {
	    port = val;
	}
    }

    /* initialize the server socket */

    l_addr.sin_family = AF_INET;
    l_addr.sin_port = htons(port);
    l_addr.sin_addr.s_addr = INADDR_ANY;

#if defined(__linux__) && defined(ENABLE_DAEMON)

    /* run as daemon */

    if (daemon(NO_CHDIR, NO_REDIR) < 0) {
	fprintf(stderr,"server: unable to become daemon: %s\n",
		strerror(errno));
	exit(1);
    }

#endif

    if ((server = socket(PF_INET, SOCK_STREAM, IPPROTO_IP)) < 0) {
	fprintf(stderr,"server: unable to create socket: %s\n",
		strerror(errno));
	exit(1);
    }

    if (bind(server, (struct sockaddr *)&l_addr, sizeof (l_addr)) < 0) {
	fprintf(stderr,"server: unable to bind socket: %s\n", 
		strerror(errno));
	exit(1);
    }

    if (listen(server, 32) < 0) {
	fprintf(stderr,"server: unable to listen on socket: %s\n",
		strerror(errno));
	exit(1);
    }

    signal(SIGTERM, terminate);
    signal(SIGINT, terminate);

    size = sizeof (r_addr);

    /* listen for connections */

    for (;;) {

	client = accept(server, (struct sockaddr *)&r_addr, &size);

	if (client < 0) {

	    fprintf(stderr, "server: unable accept connection: %s\n",
		    strerror(errno));

	} else if (numthreads < MAX_THREADS) {

	    fprintf(stderr, "==> connection accepted from %s:%d\n",
		    inet_ntoa(r_addr.sin_addr), ntohs(r_addr.sin_port));

	    pthread_mutex_lock(&thread_mutex);

	    if (pthread_create(&thread[numthreads], NULL, handler, &client)) {

		fprintf(stderr, "server: unable to handle connection: %s\n",
			strerror(errno));

	    } else if (pthread_detach(thread[numthreads])) {

		fprintf(stderr, "server: unable to detach thread: %s\n",
			strerror(errno));

	    } else { /* succesfully created and detached thread */

		numthreads++;

	    }

	    pthread_mutex_unlock(&thread_mutex);

	} else {

	    fprintf(stderr, "server: too many connections!");
	}
    }

    return 0;
}
