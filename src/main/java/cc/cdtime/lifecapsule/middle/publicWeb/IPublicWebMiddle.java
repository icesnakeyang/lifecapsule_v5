package cc.cdtime.lifecapsule.middle.publicWeb;

import cc.cdtime.lifecapsule.meta.notePublic.NotePublic;
import cc.cdtime.lifecapsule.meta.notePublic.NotePublicView;

import java.util.ArrayList;
import java.util.Map;

public interface IPublicWebMiddle {
    void createNotePublic(NotePublic notePublic) throws Exception;

    ArrayList<NotePublicView> listNotePublic(Map qIn) throws Exception;

    NotePublicView getNotePublic(String noteId) throws Exception;

    void updateNotePublic(Map qIn) throws Exception;
}
