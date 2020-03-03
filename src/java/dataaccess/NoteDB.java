package dataaccess;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import models.Note;

public class NoteDB 
{
    public int insert(Note note) throws NotesDBException 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {   
            trans.begin();
            em.merge(note);
            trans.commit();
            return 1;
        } 
        catch (Exception ex) 
        {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot insert " + note.toString(), ex);
            throw new NotesDBException("Error inserting note");
        } 
        finally 
        {
            em.close();
        }
    }

    public int update(Note note) throws NotesDBException 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {
            //begin transaction
            trans.begin();
            
            //update database
            em.merge(note);
            
            //save changes
            trans.commit();
            
            return 1;
        } 
        catch (Exception ex) 
        {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot update " + note.toString(), ex);
            throw new NotesDBException("Error updating note"); // or throw ex;
        } 
        finally 
        {
            em.close();
        }
    }

    public List<Note> getAll() throws NotesDBException 
    {
        // PPT pg.30
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try 
        {
            // createNamedQuery's "Note.findAll" is from Note.java class @NamedQuery annotations at the top
            List<Note> users = em.createNamedQuery("Users.findAll", Note.class).getResultList();
            return users;                
        } 
        finally 
        {
            em.close();
        }
    }

    /**
     * Get a single user by their username.
     *
     * @param username The unique username.
     * @return A User object if found, null otherwise.
     * @throws NotesDBException
     */
    public Note getNote(int noteID) throws NotesDBException 
    {
        // PPT pg.27
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try 
        {
            Note note = em.find(Note.class, noteID);
            return note;
        } 
	finally 
        {
            em.close();
        }
    }

    public int delete(Note note) throws NotesDBException 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {           
            // delete note
            em.remove(em.merge(note));
            trans.commit();
            
            return 1;
        } 
        catch (Exception ex) 
        {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot delete " + note.toString(), ex);
            throw new NotesDBException("Error deleting note");
        } 
        finally 
        {
            em.close();
        }
    }
}
