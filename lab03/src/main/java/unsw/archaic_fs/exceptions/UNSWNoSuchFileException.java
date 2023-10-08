package unsw.archaic_fs.exceptions;

import java.io.IOException;
public class UNSWNoSuchFileException extends IOException {
    public UNSWNoSuchFileException(String message) {
        super(message);
    }
}
