package se.monstermannen.discordmonsterbot.util;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;

import java.io.IOException;
import java.util.List;

/**
 * Youtube
 */
public class YouTubeGetter {

    public static SearchResult getID(String searchQuery){
        YouTube youtube;
        long NR_VIDEOS_TO_GET = 1;

        try {
            // This object is used to make YouTube Data API requests
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("DMonsterBot-youtube-search").build();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key
            search.setKey(DiscordMonsterBot.YT_APIKEY);

            // search words
            search.setQ(searchQuery);

            // Restrict the search results to only include videos
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");

            // number of videos to retrieve
            search.setMaxResults(NR_VIDEOS_TO_GET);

            // Call the API and print results
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();

            if(searchResultList != null){
                if(searchResultList.size() > 0) {
                    SearchResult singleVideo = searchResultList.get(0);
                    ResourceId rId = singleVideo.getId();

                    // Confirm that the result represents a video. Otherwise, the
                    // item will not contain a video ID
                    if (rId.getKind().equals("youtube#video")) {
                        return singleVideo;
                    }
                }
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }
}
