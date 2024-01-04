package main;

import java.time.LocalDateTime;

/**
 * Classe utilisée dans InterfaceDemandeVisite
 */
class DateEntry {
    private final LocalDateTime dateTime;
    private final String formattedDate;

    public DateEntry(LocalDateTime dateTime, String formattedDate) {
        this.dateTime = dateTime;
        this.formattedDate = formattedDate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return formattedDate;
    }
}