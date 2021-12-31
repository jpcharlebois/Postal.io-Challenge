package com.postalio.challenge.part2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class UnZipReader {
    
    public static void main(String[] args) throws IOException {

        String url = "http://www.sec.gov/dera/data/Public-EDGAR-log-file-data/2017/Qtr2/log20170630.zip";
        ReadableByteChannel readChannel = Channels.newChannel(new URL(url).openStream());
        File zipFile = new File("log20170630.zip");
        FileOutputStream fileOS = new FileOutputStream(zipFile);
        FileChannel writeChannel = fileOS.getChannel();
        writeChannel
            .transferFrom(readChannel, 0, Long.MAX_VALUE);
                           
        fileOS.close();
        zipFile.delete();
        //JSONObject json = Util.parseJson(response);
        System.out.println("space");

    }

}
