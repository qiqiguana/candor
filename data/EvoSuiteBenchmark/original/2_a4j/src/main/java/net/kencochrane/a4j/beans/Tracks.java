/*
Copyright (c) 2003, Ken Cochrane
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    * Neither the name of Ken Cochrane nor the names of its contributors may be used to endorse
    or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package net.kencochrane.a4j.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 14, 2003
 * Time: 9:14:58 PM
 *
 *
 */
public class Tracks implements Serializable {

    ArrayList tracks;

    public String[] getTrack() {
        String[] retTracks = new String[tracks.size()];
        if (tracks.size() > 0) tracks.toArray(retTracks);

        return retTracks;
    }

    public void setTrack(String[] newTracks) {
        tracks = new ArrayList(newTracks.length);
        for (int i = 0; i < newTracks.length; i++) {
            tracks.add(newTracks[i]);
        }

    }

    public ArrayList getTracksArray() {
        return tracks;
    }

    public String getTrack(int index) {
        String retTrack = null;

        if (tracks.size() - 1 < index) {
            retTrack = (String) tracks.get(index);
        }

        return retTrack;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();
        String track = new String();
        if (tracks != null && tracks.size() > 0) {
            output.append("# of Tracks = " + tracks.size() + "\n");
            for (int x = 0; x < tracks.size(); x++) {
                track = tracks.get(x).toString();
                if (track != null) {
                    output.append("Track - " + track + "\n");
                }
            }
        } else {
            output.append("Tracks is null or size 0" + "\n");
        }
        return output.toString();
    }
}
