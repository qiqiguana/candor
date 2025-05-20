package org.asphodel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;

/**
 * @author miracle sun
 *         Date: Mar 25, 2007
 *         Time: 8:13:59 PM
 * @since 0.1
 */
public class DefaultRepositoryManager implements RepositoryManager {
    private static Log log = LogFactory.getLog(DefaultRepositoryManager.class);

    private File repositoryHouse;
    private String repositoryHousePath;


    public DefaultRepositoryManager() {
        this.repositoryHousePath = AsphodelConfig.getRepositoryHousePath();
        this.repositoryHouse = new File(repositoryHousePath);
    }


    /**
     * change current repositoryHouse path
     *
     * @param repositoryHousePath new repositoryHouse path
     */
    public void changeRepositoryHouse(String repositoryHousePath) {
        this.repositoryHousePath = repositoryHousePath;
        this.repositoryHouse = new File(repositoryHousePath);
    }

    public String getRepositoryHousePath() {
        return repositoryHousePath;
    }

    public void setRepositoryHousePath(String repositoryHousePath) {
        this.repositoryHousePath = repositoryHousePath;
        //todo if you using a IOC ,then the repository should be set here.
        /* if(this.repositoryHouse==null) this.repositoryHouse = new File(repositoryHousePath);*/
    }

    public String getRepositoryHouse() {
        try {
            return this.repositoryHouse.getCanonicalPath();
        } catch (IOException ioe) {
            return null;
        }
    }

    public String createRepository(String repositoryIdentifier) throws FtrException {
        try {
            File indexRepository = new File(this.repositoryHouse, repositoryIdentifier);
            if (!indexRepository.exists()) {
                indexRepository.mkdirs();
                IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory(indexRepository), this.getAnalyzer(), true);
                indexWriter.close();
            }
            return indexRepository.getCanonicalPath();
        } catch (IOException e) {
            log.error("create repository error.", e);
            throw new FtrException("repository created error:", e);
        }

    }


    /**
     * delete the given repository,include all the index it contains
     */
    public void deleteRepository(String repositoryIdentifier) throws FtrException {
        File repositoryDir = new File(this.repositoryHouse, repositoryIdentifier);
        if (!repositoryDir.exists()) {
            throw new FtrException("repository " + repositoryDir.getAbsolutePath() + " does not exist!");
        }

        if (!this.deleteDirectoryRecursively(repositoryDir)) {
            throw new FtrException("delete repository fail:" + repositoryDir.getAbsolutePath());
        }
        log.info(repositoryDir.getAbsolutePath() + " deleted successfully.");
    }

    public void deleteAllRepositories() throws FtrException {
        this.deleteDirectoryRecursively(this.repositoryHouse);
        log.info("all repositories deleted.");
    }

    //todo delete dir recursively
    private boolean deleteDirectoryRecursively(File file) {
        boolean isDelSuccessful = false;

        if (file.isDirectory()) {
            File subfiles[] = file.listFiles();
            if (subfiles != null) {
                for (int i = 0; i < subfiles.length; i++) {
                    File tempFile = subfiles[i];
                    isDelSuccessful = deleteDirectoryRecursively(tempFile);
                    //even if only one failed,we should return ;
                    if (!isDelSuccessful) {
                        return isDelSuccessful;
                    }
                }
            }
        }

        isDelSuccessful = file.delete();

        return isDelSuccessful;
    }

    public boolean exist(String repositoryIdentifier) {
        return (new File(this.getRepositoryHouse(), repositoryIdentifier)).exists();
    }


    /***/
    public void optimizeRepository(String repositoryIdentifier) throws FtrException {
        if (repositoryIdentifier == null) {
            repositoryIdentifier = AsphodelConfig.getDefaultRepository();
        }
        try {
            File indexRepository = new File(this.repositoryHouse, repositoryIdentifier);
            if (indexRepository.exists()) {
                IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory(indexRepository), this.getAnalyzer(), true);
                indexWriter.optimize();
                indexWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * <p/>
     * list all the repositories under the current repositoryHouse
     * with lucene implementation,the repository dir contains file named segments***
     * </p>
     */
    public Collection<String> getAllRepositories() {
        Collection<String>repositoryIdentifiers = new ArrayList<String>();
        File []repositories = this.repositoryHouse.listFiles();
        if (repositories != null) {
            for (int i = 0; i < repositories.length; i++) {
                if (this.isRepository(repositories[i]))
                    repositoryIdentifiers.add(repositories[i].getName());
            }
        }
        return repositoryIdentifiers;
    }

    private boolean isRepository(File repository) {
        final String _lucene_segments_file = "segments";

        if (repository.isDirectory()) {
            File[] underFiles = repository.listFiles();
            if (underFiles != null) {
                for (int i = 0; i < underFiles.length; i++) {
                    File tempfile = underFiles[i];
                    if (!tempfile.isDirectory() && tempfile.getName().indexOf(_lucene_segments_file) > -1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Analyzer getAnalyzer() {
        return new StandardAnalyzer();
    }
}
