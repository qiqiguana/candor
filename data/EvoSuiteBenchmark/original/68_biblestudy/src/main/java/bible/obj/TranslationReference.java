package bible.obj;

/**
 * 
 * @author  James Stauffer
 * @version $Id: TranslationReference.java,v 1.1 2001/02/21 03:08:49 jstauffe Exp $
 */
public class TranslationReference {
    public TranslationReference(Translation translation, Reference ref) {
        this.translation = translation;
        this.ref = ref;
    }

    public Verse getVerse() {
        return Verse.New(Verse.GetId(translation.getId(), ref.getBookId(), ref.getChapterNo(), ref.getVerseNo()));
    }

    public Translation getTranslation() {
        return translation;
    }

    public Reference getReference() {
        return ref;
    }

    public String toString() {
        return getClass().getName() + ":[" + translation + " " + ref + "]";
    }

    private Translation translation;
    private Reference ref;

}

