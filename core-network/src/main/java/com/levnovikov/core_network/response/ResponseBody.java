package com.levnovikov.core_network.response;

import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class ResponseBody {

    private final InputStream contentStream;
    private String mediaType;
    private String encoding;

    public ResponseBody(InputStream contentStream, String encoding, String mediaType) {
        this.contentStream = contentStream;
        this.encoding = encoding;
        this.mediaType = mediaType;
    }

    public InputStream getContentStream() {
        return contentStream;
    }

    public String getContentString() throws IOException {
        return readStringFromStream(contentStream);
    }

    private String readStringFromStream(InputStream input) throws IOException {
        String content = CharStreams.toString(new InputStreamReader(input, encoding));
        Closeables.closeQuietly(input);
        return content;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getMediaType() {
        return mediaType;
    }
}
