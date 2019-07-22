package dynamusic;

import atg.process.ProcessExecutionContext;
import atg.process.ProcessException;

import atg.process.action.ActionImpl;
import atg.repository.Repository;

import java.util.Collection;

// DAS classes
import atg.beans.DynamicBeans;
import atg.beans.PropertyNotFoundException;
import atg.repository.RepositoryItem;

/**
 * This class is a custom scenario action.
 */
public class NewItemAddToProfile extends ActionImpl {


    public void executeAction(ProcessExecutionContext pContext) throws ProcessException {

        //1: get a handle to the profile (note this is to be used for a global event,
        //		but with an *individual* action execution policy)
        RepositoryItem profile = null;
        profile = pContext.getSubject();

        // Id of new song item
        String theId = null;

        try {

            //2: retrieve id, title, and genre from the new song item

            NewSongMessage newSongMessage = (NewSongMessage) pContext.getMessage();
            theId = newSongMessage.getSongId();

            //
            // Chapter 7, Exercise 1 - place code here
            //


            // A) get reference to configured repository item
            RepositoryItem theItem = null;
            Repository theRepository = null;
            theRepository = (Repository) pContext.resolveName("/dynamusic/SongsRepository");
            theItem = (RepositoryItem) theRepository.getItem(theId, "song");
            System.out.println("New Item, retrieved from repository, is: " + theItem);

            RepositoryItem nSP = (RepositoryItem) DynamicBeans.getPropertyValue(profile, "newSongsPlaylist");
            Collection theItems = (Collection) DynamicBeans.getPropertyValue(nSP, "songs");

            System.out.println("current items is/are: " + theItems);

            // B) load the new item to the collection
            try {
                theItems.add(theItem);
                System.out.println("theItems is now: " + theItems);
                DynamicBeans.setPropertyValue(nSP, "songs", theItems);
            } catch (Exception e) {
                System.out.println("Cannot add song to playlist" + e);
            }


        } catch (PropertyNotFoundException pnfe) {
            throw new ProcessException(pnfe);
        } catch (atg.repository.RepositoryException e) {
            System.out.println("Trouble adding song to playlist" + e);
        }


    }
}


