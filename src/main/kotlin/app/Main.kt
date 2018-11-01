@file:Suppress("KDocUnresolvedReference")

package app

import app.transfer.dao.TransferDao
import spark.Request
import spark.Spark.*

fun main(args: Array<String>) {
	
	exception(Exception::class.java) { e, _, _ -> e.printStackTrace() }
	
	val transferDao = TransferDao()
	
	path("/transfers") {
		
		/**
		 * @return all Transfers
		 */
		get("") { _, _ ->
			transferDao.allTransfers()
		}
		
		/**
		 * finds one Transfer entity by id
		 * @param id Int
		 * @return Transfer entity
		 */
		get("/:id") { req, _ ->
			transferDao.findById(req.params("id").toInt())
		}
		
		/**
		 * Creates one Transfer entity
		 * @param payee String
		 * @param receiver String
		 * @param amount Double
		 * @param success Boolean
		 * @return Transfer entity
		 */
		post("/create") { req, res ->
			transferDao.save(
				payee = req.qp("payee"),
				receiver = req.qp("receiver"),
				amount = req.qp("amount").toDouble(),
				success = req.qp("success").toBoolean()
			)
		}
		
		/**
		 * Updates one Transfer entity based on id
		 * @param id Int
		 * @param payee String
		 * @param receiver String
		 * @param amount Double
		 * @param success Boolean
		 * @return Transfer entity
		 */
		patch("/update/:id") { req, _ ->
			transferDao.update(
				id = req.params("id").toInt(),
				payee = req.qp("payee"),
				receiver = req.qp("receiver"),
				amount = req.qp("amount").toDouble(),
				success = req.qp("success").toBoolean()
			)
		}
		
		/**
		 * deletes one Transfer entity based on id
		 * @param id Int
		 * @return DeletionStatus
		 */
		delete("/delete/:id") { req, _ ->
			transferDao.delete(req.params("id").toInt())
		}
		
	}
	
	transferDao.transfers.forEach(::println)
	
}

fun Request.qp(key: String): String = this.queryParams(key)
