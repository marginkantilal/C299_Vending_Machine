package com.vm.service;

import com.vm.dao.VMAuditDao;
import com.vm.exception.VendingMachinePersistenceException;

public class VendingMachineAuditDaoStubImpl implements VMAuditDao {
	@Override
	public void writeAuditLogEntry(String entry) throws VendingMachinePersistenceException{
		
	}

}
