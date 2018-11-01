package com.revolut

import app.transfer.util.DeletionStatus
import app.transfer.dao.TransferDao
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TransferTest {
	private val transferDao = TransferDao()
	
	/*
	Tries to create a Transfer Entity
	 */
	@Test
	fun createTest() {
		val transfer = transferDao.save("8678BKSJ", "908798BJJS", 129.85, true)
		assertNotNull(transfer)
	}
	
	/*
	Tries to find a Transfer Entity after creating it.
	 */
	@Test
	fun findTest() {
		var transfer = transferDao.save("8678BKSJ", "908798BJJS", 129.85, true)
		if (transfer != null) {
			transfer = transferDao.findById(transfer.id)
		}
		assertNotNull(transfer)
	}
	
	/*
	Tries to update a Transfer Entity after creating it.
	 */
	@Test
	fun updateTest() {
		transferDao.save("8678BKSJ", "908798BJJS", 129.85, true)
		val transfer = transferDao.update(0, "updatedId", "908798BJJS", 129.85, true)
		assertEquals("updatedId", transfer?.payee)
	}
	
	/*
	Tries to delete a Transfer Entity after creating it.
	 */
	@Test
	fun deletionTest() {
		val transfer = transferDao.save("8678BKSJ", "908798BJJS", 129.85, true)
		val deletionStatus = transferDao.delete(transfer?.id!!)
		assertEquals(DeletionStatus.DELETED, deletionStatus)
	}
	
	/*
	Tries to delete a Transfer Entity which does not exist
	 */
	@Test
	fun emptyDeletionTest() {
		val deletionStatus = transferDao.delete(0)
		assertEquals(DeletionStatus.NOT_FOUND, deletionStatus)
	}
}