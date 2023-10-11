package unsw.file;

import unsw.response.models.FileInfoResponse;

public class File {
    private String fileName;
    private String content;
    private int size;
    private String from;
    private String to;
    private boolean status;
    private int onTransferSize;
    private String onTransferContent;

    /// Constructor ///
    /**
     * Constructor for File.
     * @param fileName
     * @param content
     */
    public File(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.size = content.length();
        this.from = "";
        this.to = "";
        this.status = false;
        this.onTransferSize = 0;
        this.onTransferContent = "";
    }

    /// Helper Functions ///
    /**
     * Get the file info.
     * @return FileInfoResponse
     */
    public FileInfoResponse getFileInfo() {
        return new FileInfoResponse(fileName, onTransferContent, size, status);
    }

    /// Getters and Setters ///
    /**
     * Get the file name.
     * @return String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the file name.
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get the file content.
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the file content.
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
        this.size = content.length();
    }

    /**
     * Get the file size.
     * @return int
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the file size.
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Get the file sender.
     * @return String
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set the file sender.
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Get the file receiver.
     * @return String
     */
    public String getTo() {
        return to;
    }

    /**
     * Set the file receiver.
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Get the file status.
     * @return boolean
     */
    public boolean getIsComplete() {
        return this.status;
    }

    /**
     * Set the file status.
     */
    public void setIsComplete() {
        if (onTransferSize == size) {
            status = true;
        } else {
            status = false;
        }
    }

    /**
     * Get the file on transfer size.
     * @return int
     */
    public int getOnTransferSize() {
        return onTransferSize;
    }

    /**
     * Set the file on transfer size.
     * @param onTransferSize
     */
    public void setOnTransferSize(int onTransferSize) {
        if (onTransferSize > this.size) {
            onTransferSize = this.size;
        }
        this.onTransferSize = onTransferSize;
        setTransferContent();
    }

    /**
     * Get the file on transfer content.
     * @return String
     */
    public String getOnTransferContent() {
        return onTransferContent;
    }

    /**
     * Get the file remain content.
     * @return String
     */
    public String getRemainContent() {
        return content.substring(onTransferSize);
    }

    /**
     * Set the file on transfer content.
     */
    public void setTransferContent() {
        onTransferContent = content.substring(0, onTransferSize);
    }

    /**
     * Set the file on transfer content.
     * @param onTransferContent
     */
    public void setOnTransferContent(String onTransferContent) {
        this.onTransferContent = onTransferContent;
    }
}
