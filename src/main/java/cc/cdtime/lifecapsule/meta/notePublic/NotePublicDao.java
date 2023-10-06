package cc.cdtime.lifecapsule.meta.notePublic;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface NotePublicDao {
    void createNotePublic(NotePublic notePublic);

    ArrayList<NotePublicView> listNotePublic(Map qIn);

    NotePublicView getNotePublic(String noteId);

    void updateNotePublic(Map qIn);

}
