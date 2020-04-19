package com.github.siberianintegrationsystems.restApp.controller.dto.journal;

import java.util.List;

public class JournalResultDTO {
    public int total;
    public List<? extends JournalItemDTO> items;

    public JournalResultDTO(int total, List<? extends JournalItemDTO> item) {
        this.total = total;
        this.items = item;
    }
}
