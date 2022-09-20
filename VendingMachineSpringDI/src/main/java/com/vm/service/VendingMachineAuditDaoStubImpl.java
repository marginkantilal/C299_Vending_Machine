package com.vm.service;

import com.vm.dao.VendingMachineAuditDao;
import com.vm.exception.VendingMachinePersistenceException;

public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {

	@Override
	public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
		
	}

}
