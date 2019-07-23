package dynamusic;

import atg.process.ProcessExecutionContext;
import atg.process.ProcessException;

import atg.process.action.ActionImpl;
import atg.repository.Repository;

import java.util.Collection;
import java.util.Map;

// DAS classes
import atg.beans.DynamicBeans;
import atg.beans.PropertyNotFoundException;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

/**
 * This class is a custom scenario action.
 */
public class NewItemAddToProfile extends ActionImpl {

    private static final String PARAM_NEW_SONGS_PLAYLIST_LIMIT = "newSongsPlaylistLimit";
    private static final String SONGS_REPOSITORY_NAME = "/dynamusic/SongsRepository";
    private static final String SONG_DESCRIPTOR_NAME = "song";
    private static final String PROPERTY_NEW_SONGS_PLAYLIST = "newSongsPlaylist";
    private static final String PROPERTY_SONGS = "songs";
    private static final String CANNOT_ADD_SONG_TO_PLAYLIST = "Cannot add song to playlist";
    private static final String TROUBLE_ADDING_SONG_TO_PLAYLIST = "Trouble adding song to playlist";

    @Override
    public void initialize(Map pParameters) throws ProcessException {
        storeOptionalParameter(pParameters, PARAM_NEW_SONGS_PLAYLIST_LIMIT, Integer.class);
    }

    public void executeAction(ProcessExecutionContext pContext) throws ProcessException {
        Object newSongsPlaylistLimitObject = getParameterValue(PARAM_NEW_SONGS_PLAYLIST_LIMIT, pContext);
        if (newSongsPlaylistLimitObject == null) return; //ATG Start Exception fix
        int newSongsPlaylistLimit = (Integer) newSongsPlaylistLimitObject;

        RepositoryItem profile = pContext.getSubject();
        String theId;

        try {
            //retrieve id, title, and genre from the new song item
            NewSongMessage newSongMessage = (NewSongMessage) pContext.getMessage();
            theId = newSongMessage.getSongId();


            // get reference to configured repository item
            RepositoryItem theItem;
            Repository theRepository;
            theRepository = (Repository) pContext.resolveName(SONGS_REPOSITORY_NAME);
            theItem = theRepository.getItem(theId, SONG_DESCRIPTOR_NAME);

            RepositoryItem nSP = (RepositoryItem) DynamicBeans.getPropertyValue(profile, PROPERTY_NEW_SONGS_PLAYLIST);
            Collection<RepositoryItem> theItems = (Collection) DynamicBeans.getPropertyValue(nSP, PROPERTY_SONGS);

            // load the new item to the collection
            try {
                if (theItems.size() < newSongsPlaylistLimit) {
                    theItems.add(theItem);
                }
                DynamicBeans.setPropertyValue(nSP, PROPERTY_SONGS, theItems);
            } catch (Exception e) {
                System.out.println(CANNOT_ADD_SONG_TO_PLAYLIST + e);
            }


        } catch (PropertyNotFoundException pnfe) {
            throw new ProcessException(pnfe);
        } catch (RepositoryException e) {
            System.out.println(TROUBLE_ADDING_SONG_TO_PLAYLIST + e);
        }

    }
}


