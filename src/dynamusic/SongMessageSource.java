package dynamusic;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import atg.dms.patchbay.MessageSource;
import atg.dms.patchbay.MessageSourceContext;

/**
 * This component fires a NewSongMessage event using JMS.
 * <p>
 * Note that since it makes use of DMS and PatchBay it does not have any knowledge
 * of where the message is being sent.  See the dynamoMessagingSystem.xml file
 * for the destination of these messages (ultimately they will go to the ScenarioManager
 * component).
 */
public class SongMessageSource extends atg.nucleus.GenericService implements MessageSource {
    private static final String JMS_TYPE_NEW_SONG_MESSAGE = "NewSongMessage";
    private static final String MESSAGE_SENT_SONG_ID = "fireMessage(): message sent, songId: ";
    private MessageSourceContext mContext;
    private boolean mStarted;

    // These methods implement the MessageSource interface
    public void setMessageSourceContext(MessageSourceContext pContext) {
        mContext = pContext;
    }

    public void startMessageSource() {
        mStarted = true;
    }

    public void stopMessageSource() {
        mStarted = false;
    }

    // This method will send a message
    public void fireMessage(String pSongId, String pSongGenre, String pTitle) throws JMSException {
        if (mStarted) {
            ObjectMessage msg = mContext.createObjectMessage();

            NewSongMessage newSongMessage = new NewSongMessage(pSongId, pSongGenre, pTitle);
            msg.setJMSType(JMS_TYPE_NEW_SONG_MESSAGE);
            msg.setObject(newSongMessage);
            mContext.sendMessage(msg);
            if (isLoggingDebug()) logDebug(MESSAGE_SENT_SONG_ID + pSongId);
        }
    }
}