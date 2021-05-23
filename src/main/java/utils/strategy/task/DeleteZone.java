package utils.strategy.task;

import utils.strategy.Tache;

public class DeleteZone extends Tache {

    public DeleteZone(String desc, String itemId) {
        super(desc, 0, Type.ELEMENT, SubType.SUPPRESSION, itemId, Mirror.MIRRORY);
    }

    public DeleteZone(String desc, String itemId, Mirror mirror) {
        super(desc, 0, Type.ELEMENT, SubType.SUPPRESSION, itemId, mirror);
    }
}
