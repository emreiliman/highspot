package steps;

import java.util.List;

import data.ChangeData;
import repository.IRepository;

public interface IChangesApplier {
    public void applyChanges(IRepository r, List<ChangeData> changes) throws Exception;
}
