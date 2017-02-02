package se.monstermannen.discordmonsterbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Download youtube videos to mp3
 */
public class YTDownloader {
    /**
     * Download a youtube video to mp3
     *
     * @param url to youtube video
     * @return path to song if success or an error message starting with 'ERROR:' if not
     */
    public static String download(String url){

        String line = "ERROR";

        try {
            // write to bat file
            PrintWriter writer = new PrintWriter("youtube-dl.bat", "UTF-8");
            writer.println("python youtube-dl " + url + " --extract-audio --audio-format mp3 --output "
                    + "youtube-downloads/" + "%%(title)s.%%(ext)s");
            writer.close();

            // execute bat file.
            // python script runs.
            // /B to prevent cmd from opening.
            Process process = Runtime.getRuntime().exec("cmd /c start /B youtube-dl.bat");
            process.waitFor();
            process.destroy();

            // read youtube-dl script output to get new filename
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while((line = in.readLine()) != null){
                System.out.println("line: " + line);
                if(line.contains("Destination: ") && line.contains(".mp3")){
                    return line.substring(line.indexOf("Destination") + "Destination".length() + 2);    // return path
                }else if(line.contains("ERROR:")){
                    return "ERROR - " + line;    // return error message
                }
            }

            // cut out only path + songname


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return line;    // return path to downloaded song
    }
}
