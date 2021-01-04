package actions;

import repository.IRepository;

public interface IAction {
    public void apply(IRepository r) throws Exception;
}
