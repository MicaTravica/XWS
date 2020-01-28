package com.example.xmlScientificPublicationEditor.model;

public enum ProcessState {
    IN_PROGRESS("inProgress"),
    FOR_REVIEW("forReview"),
    WAITING_FOR_REVIEWERS("waitningForReviewers"),
    SCORED("scored"),
    PUBLISHED("published"),
    REJECTED("rejected"),
    REVISED("revised"),
    RETRACTED("retracted"),
    DELETED("deleted");

    private String action; 

    public String getAction() 
    { 
        return this.action; 
    } 
    // enum constructor - cannot be public or protected 
    private ProcessState(String action) 
    { 
        this.action = action; 
    }
}
