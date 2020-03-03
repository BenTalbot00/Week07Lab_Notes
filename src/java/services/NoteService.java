package services;

import dataaccess.NoteDB;
import java.sql.Date;
import java.util.List;
import models.Note;

public class NoteService 
{
    private NoteDB noteDB;

    public NoteService() 
    {
        noteDB = new NoteDB();
    }

    public Note get(int noteID) throws Exception 
    {
        return noteDB.getNote(noteID);
    }

    public List<Note> getAll() throws Exception 
    {
        return noteDB.getAll();
    }

    public int update(int noteID, Date dateCreated, String title, String contents) throws Exception 
    {
        Note note = get(noteID);
        note.setDatecreated(dateCreated);
        note.setTitle(title);
        note.setContents(contents);

        return noteDB.update(note);
    }

    public int delete(int noteID) throws Exception 
    {
        Note deletedNote = noteDB.getNote(noteID);
        
        return noteDB.delete(deletedNote);
    }

    public int insert(int noteID, Date dateCreated, String title, String contents) throws Exception 
    {
        Note note = new Note(noteID, dateCreated, title, contents);
        return noteDB.insert(note);
    }
}
