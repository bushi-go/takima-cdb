package fr.takima.cdb.services.computer.specifications;

import fr.takima.cdb.commons.Specification;
import fr.takima.cdb.model.computer.Computer;

public class ComputerDateSpecification implements Specification<Computer>{

    public static ComputerDateSpecification getComputerDateSpecification(){
        return new ComputerDateSpecification();
    }

	@Override
	public boolean isSatisfiedBy(Computer entity) {
		return entity.getIntroduced().isBefore(entity.getDiscontinued());
	} 
}