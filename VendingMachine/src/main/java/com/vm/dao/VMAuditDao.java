package com.vm.dao;

import com.vm.exception.VendingMachinePersistenceException;

public interface VMAuditDao {

		public void writeAuditLogEntry(String entry) throws VendingMachinePersistenceException;

	
}
