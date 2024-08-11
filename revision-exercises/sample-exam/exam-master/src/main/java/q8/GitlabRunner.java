package q8;

public class GitlabRunner {
    public void run(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
        }
    }
}