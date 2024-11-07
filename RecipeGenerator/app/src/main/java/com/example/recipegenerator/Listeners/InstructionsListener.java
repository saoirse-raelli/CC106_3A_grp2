package com.example.recipegenerator.Listeners;

import com.example.recipegenerator.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
