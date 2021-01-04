package steps;

import java.util.List;
import java.util.stream.Collectors;

import actions.ActionFactory;
import actions.IAction;
import data.ChangeData;
import repository.IRepository;

public class ChangesApplier implements IChangesApplier {

    @Override
    public void applyChanges(IRepository repository, List<ChangeData> changes) throws Exception {
        List<IAction> actions = changes.stream().map(change -> ActionFactory.create(change)).collect(Collectors.toList());
        for(IAction action : actions) {
            action.apply(repository);
        }
    }
}
