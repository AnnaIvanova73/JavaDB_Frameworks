package softuni.labmapping.services;

import softuni.labmapping.domain.dtos.view.ManagerView;

import java.util.List;

public interface ManagerService {

    public void setManagers();
    public List<ManagerView> printAllManagersWithTheirEmployees ();
}
