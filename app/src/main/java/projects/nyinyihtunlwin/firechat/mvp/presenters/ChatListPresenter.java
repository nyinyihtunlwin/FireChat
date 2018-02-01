package projects.nyinyihtunlwin.firechat.mvp.presenters;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import projects.nyinyihtunlwin.firechat.FireChatApp;
import projects.nyinyihtunlwin.firechat.data.models.FireChatModel;
import projects.nyinyihtunlwin.firechat.mvp.views.ChatListView;

/**
 * Created by Dell on 2/1/2018.
 */

public class ChatListPresenter extends BasePresenter<ChatListView> {

    private static ChatListPresenter objectInstance;

    private String mCurrentConversationId;


    private ChatListPresenter() {
    }

    public static ChatListPresenter getInstance() {
        if (objectInstance == null) {
            objectInstance = new ChatListPresenter();
        }
        return objectInstance;
    }

    @Override
    public void onCreate(ChatListView view) {
        super.onCreate(view);
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void onTapConversation(String conversationId) {

        mCurrentConversationId = conversationId;

        if (FireChatModel.getInstance().isUserAuthenticate()) {
            mView.navigateToConversationScreen(conversationId);
        } else {
            mView.showAuthenticationDialog();
        }
    }

    public void onSuccessGoogleSignIn(GoogleSignInAccount signInAccount) {
        FireChatModel.getInstance().authenticateUserWithGoogleAccount(signInAccount, new FireChatModel.UserAuthenticateDelegate() {
            @Override
            public void onSuccessAuthenticate(GoogleSignInAccount signInAccount) {
                mView.navigateToConversationScreen(mCurrentConversationId);
            }

            @Override
            public void onFailureAuthenticate(String errrorMsg) {
                mView.showErrorInfo(errrorMsg);
            }
        });
    }
}