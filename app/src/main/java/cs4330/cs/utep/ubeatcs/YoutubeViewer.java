package cs4330.cs.utep.ubeatcs;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * @author Isaias Leos
 */
public class YoutubeViewer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private String youtubeID = "";
    private YouTubePlayerView youTubeView;

    /**
     * Initialize the youtubeView within the viewer_youtube.xml given by the youtube API.
     * Also obtain the youtubeID from the youtube view list.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_youtube);
        youtubeID = getIntent().getStringExtra("youtubeID");
        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
    }

    /**
     * Plays the youtube video after initialization.
     *
     * @param provider
     * @param player
     * @param wasRestored
     */
    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            if (youtubeID.contains("watch?v")) {
                String[] temp = youtubeID.split("=");
                player.cueVideo(temp[temp.length - 1]);
            } else {
                String[] temp = youtubeID.split(".be/");
                player.cueVideo(temp[temp.length - 1]);
            }
        }
    }

    /**
     * Failure to play the video due to not initialing correctly.
     *
     * @param provider
     * @param errorReason
     */
    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Obtain intent data
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    /**
     * Get youtube player provider.
     *
     * @return
     */
    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    /**
     * Youtube API Key.
     */
    public final class Config {
        static final String YOUTUBE_API_KEY = "AIzaSyAoXzjItmyCNqDipAOGjqGRuw5u9nZh_Q8";

        private Config() {
        }
    }

}

