/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
databaseChangeLog = {

	changeSet(author: "mlist (generated)", id: "1341390193381-1") {
		createTable(tableName: "taq_man_assay") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taq_man_assayPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-2") {
		createTable(tableName: "taq_man_sample") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taq_man_samplPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "cell_line_data_id", type: "bigint")

			column(name: "inducer_id", type: "bigint")

			column(name: "sample_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "sample_type", type: "varchar(6)") {
				constraints(nullable: "false")
			}

			column(name: "taq_man_result_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-3") {
		createTable(tableName: "taqManInducer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taqManInducerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "concentration", type: "int") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-4") {
		createTable(tableName: "taqManPurificationMethod") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taqManPurificPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-5") {
		createTable(tableName: "taqManResult") {
			column(name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taqManResultPK")
			}

			column(name: "annealing_temperature", type: "int")

			column(name: "attachment_id", type: "bigint")

			column(name: "cdnatemplate", type: "varchar(5)")

			column(name: "cycle_number", type: "int")

			column(name: "detectors_assigned", type: "bit")

			column(name: "purification_method_id", type: "bigint")

			column(name: "reference_result", type: "bit")

			column(name: "reverse_transcription_kit_id", type: "bigint")

			column(name: "reverse_transcription_primer_id", type: "bigint")

			column(name: "samples_assigned", type: "bit")
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-6") {
		createTable(tableName: "taqManResult_olfDataObject") {
			column(name: "taq_man_result_data_objects_id", type: "bigint")

			column(name: "data_object_id", type: "bigint")
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-7") {
		createTable(tableName: "taqManResult_taq_man_assay") {
			column(name: "taq_man_result_detectors_id", type: "bigint")

			column(name: "taq_man_assay_id", type: "bigint")
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-8") {
		createTable(tableName: "taqManReverseTranscriptionKit") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taqManReversePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-9") {
		createTable(tableName: "taqManReverseTranscriptionPrimer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taqManReversePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-10") {
		createTable(tableName: "taqManSet") {
			column(name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "taqManSetPK")
			}

			column(name: "name", type: "varchar(255)")
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-11") {
		createTable(tableName: "taqManSet_taqManResult") {
			column(name: "taq_man_set_taq_man_results_id", type: "bigint")

			column(name: "taq_man_result_id", type: "bigint")
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-12") {
		addNotNullConstraint(columnDataType: "varchar", columnName: "xml", schemaName: "dbo", tableName: "olfBarcodeLabel")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-13") {
		addForeignKeyConstraint(baseColumnNames: "sub_data_object_id", baseTableName: "olfBarcode", constraintName: "FK2BC5421732702C0E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "olfSubDataObject", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-14") {
		addForeignKeyConstraint(baseColumnNames: "cell_line_data_id", baseTableName: "taq_man_sample", constraintName: "FK9D991BAA709D9982", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "gtCellLineData", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-15") {
		addForeignKeyConstraint(baseColumnNames: "inducer_id", baseTableName: "taq_man_sample", constraintName: "FK9D991BAA9B893537", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManInducer", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-16") {
		addForeignKeyConstraint(baseColumnNames: "taq_man_result_id", baseTableName: "taq_man_sample", constraintName: "FK9D991BAA8EB835B3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManResult", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-17") {
		addForeignKeyConstraint(baseColumnNames: "attachment_id", baseTableName: "taqManResult", constraintName: "FKFD1926D36204606C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "olfAttachment", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-18") {
		addForeignKeyConstraint(baseColumnNames: "purification_method_id", baseTableName: "taqManResult", constraintName: "FKFD1926D3523478C0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManPurificationMethod", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-19") {
		addForeignKeyConstraint(baseColumnNames: "reverse_transcription_kit_id", baseTableName: "taqManResult", constraintName: "FKFD1926D3EB9AB051", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManReverseTranscriptionKit", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-20") {
		addForeignKeyConstraint(baseColumnNames: "reverse_transcription_primer_id", baseTableName: "taqManResult", constraintName: "FKFD1926D3ED5F5323", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManReverseTranscriptionPrimer", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-21") {
		addForeignKeyConstraint(baseColumnNames: "data_object_id", baseTableName: "taqManResult_olfDataObject", constraintName: "FKC8404E26989024CF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "olfDataObject", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-22") {
		addForeignKeyConstraint(baseColumnNames: "taq_man_result_data_objects_id", baseTableName: "taqManResult_olfDataObject", constraintName: "FKC8404E26E1FB28EF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManResult", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-23") {
		addForeignKeyConstraint(baseColumnNames: "taq_man_assay_id", baseTableName: "taqManResult_taq_man_assay", constraintName: "FK4E33900DD7F616E1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taq_man_assay", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-24") {
		addForeignKeyConstraint(baseColumnNames: "taq_man_result_detectors_id", baseTableName: "taqManResult_taq_man_assay", constraintName: "FK4E33900DECFDD345", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManResult", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-25") {
		addForeignKeyConstraint(baseColumnNames: "taq_man_result_id", baseTableName: "taqManSet_taqManResult", constraintName: "FK679476C68EB835B3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManResult", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-26") {
		addForeignKeyConstraint(baseColumnNames: "taq_man_set_taq_man_results_id", baseTableName: "taqManSet_taqManResult", constraintName: "FK679476C6A739D0EA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "taqManSet", referencesUniqueColumn: "false")
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-27") {
		createIndex(indexName: "unique-data_object_id", tableName: "olfBarcode") {
			column(name: "sub_data_object_id")

			column(name: "data_object_id")
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-28") {
		createIndex(indexName: "unique-name", tableName: "taqManInducer") {
			column(name: "concentration")

			column(name: "name")
		}
	}

	changeSet(author: "mlist (generated)", id: "1341390193381-29") {
		createIndex(indexName: "name_unique_1341390189090", tableName: "taq_man_assay", unique: "true") {
			column(name: "name")
		}
	}
}
