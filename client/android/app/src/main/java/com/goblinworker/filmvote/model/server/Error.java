package com.goblinworker.filmvote.model.server;

/**
 * Object that holds error message from server.
 */
public class Error {

    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    /**
     * Checks if it's a real error.
     *
     * @return boolean
     */
    public boolean isValid() {

        if (error == null || error.isEmpty()) {
            return false;
        }

        return true;
    }

    // Getter / Setter

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
