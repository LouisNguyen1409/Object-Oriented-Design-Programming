package unsw.sso;

import unsw.sso.providers.Hoogle;
import unsw.sso.providers.LinkedOut;

public class Browser {
    private Token currentToken = null;
    private String currentPage = null;
    private String previousPage = null;
    private ClientApp currentApp = null;

    public void visit(ClientApp app) {
        this.currentToken = null;
        this.previousPage = null;
        this.currentPage = "Select a Provider";
        this.currentApp = app;
    }

    public String getCurrentPageName() {
        return this.currentPage;
    }

    public void clearCache() {
        // TODO Implement this
    }

    public void interact(Object using) {
        if (using == null) {
            this.currentPage = this.previousPage;
            return;
        }

        switch (currentPage) {
        case "Select a Provider":
            // if the currentApp doesn't have hoogle
            // then it has no providers, which just will prevent
            // transition.
            if (using instanceof Hoogle && currentApp.hasHoogle()) {
                this.previousPage = currentPage;
                this.currentPage = "Hoogle Login";
            } else if (using instanceof LinkedOut && currentApp.hasLinkedOut()) {
                this.previousPage = currentPage;
                this.currentPage = "LinkedOut Login";
                // do nothing...
            }
            break;
        case "Hoogle Login":
            if (using instanceof Token) {
                Token token = (Token) using;
                if (token.getAccessToken() != null) {
                    this.previousPage = currentPage;
                    this.currentPage = "Home";

                    this.currentToken = token;
                    this.currentApp.registerUser((Token) token);
                } else {
                    // If accessToken is null, then the user is not authenticated
                    // Go back to select providers page
                    this.currentPage = "Select a Provider";
                }
            }
            break;
        case "LinkedOut Login":
            if (using instanceof Token) {
                Token token = (Token) using;
                if (token.getAccessToken() != null) {
                    this.previousPage = currentPage;
                    this.currentPage = "Home";

                    this.currentToken = token;
                    this.currentApp.registerUser((Token) token);
                } else {
                    // If accessToken is null, then the user is not authenticated
                    // Go back to select providers page
                    this.currentPage = "Select a Provider";
                }
            }
            break;
        case "Home":
            // no need to do anything
            break;
        default:
            break;
        }
    }
}
