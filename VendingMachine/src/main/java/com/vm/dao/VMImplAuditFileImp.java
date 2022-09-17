package com.vm.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.vm.exception.VendingMachinePersistenceException;

@Component
public class VMImplAuditFileImp implements VMAuditDao {
	public static final String AUDIT_FILE = "Audit.txt";
	
	
	public void writeAuditLogEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;
	
       
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist log information.", e);
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
	}

}
