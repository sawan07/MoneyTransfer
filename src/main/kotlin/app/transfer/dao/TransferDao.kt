package app.transfer.dao

import app.transfer.util.DeletionStatus
import app.transfer.model.Transfer
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.logging.Logger


class TransferDao {
	//initializing in memory data store as a Mutable Map
	val transfers = mutableMapOf<Int, Transfer>()
	
	//initializing logger
	private val logger = Logger.getLogger(TransferDao::class.java.name)
	
	//getting the last id
	private var lastId: AtomicInteger = AtomicInteger(transfers.size - 1)
	
	//returns all Transfer entities
	fun allTransfers() = transfers
	
	//Saves single Transfer entity
	fun save(
		payee: String,
		receiver: String,
		amount: Double,
		success: Boolean
	): Transfer? {
		//autogenerating id
		val id = lastId.incrementAndGet()
		logger.info("id is $id")
		transfers[id] = Transfer(id, payee, receiver, amount, getTimestamp(), success)
		return findById(id)
	}
	
	//gets single Transfer entity based on id
	fun findById(id: Int): Transfer? {
		return transfers[id]
	}
	
	//updates single Transfer entity based on id
	fun update(
		id: Int,
		payee: String,
		receiver: String,
		amount: Double,
		success: Boolean
	): Transfer? {
		if (transfers[id] != null)
			transfers[id] = Transfer(id, payee, receiver, amount, getTimestamp(), success)
		return findById(id)
	}
	
	//deletes single Transfer entity based on id
	fun delete(id: Int): DeletionStatus {
		//checks if the id exist
		if (findById(id) != null)
			transfers.remove(id)
		else
			return DeletionStatus.NOT_FOUND
		//checks if the id exists even after the deletion operation
		return if (findById(id) == null) DeletionStatus.DELETED else DeletionStatus.DELETION_FAILED
	}
	
	//generates timestamp
	private fun getTimestamp(): Timestamp {
		return Timestamp(Date().time)
	}
}