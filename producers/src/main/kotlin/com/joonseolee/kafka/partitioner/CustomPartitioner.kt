package com.joonseolee.kafka.partitioner

import mu.KotlinLogging
import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.clients.producer.internals.StickyPartitionCache
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.utils.Utils

class CustomPartitioner : Partitioner {
    private lateinit var specialKeyName: String
    private val stickyPartitionCache = StickyPartitionCache()

    override fun configure(configs: MutableMap<String, *>?) {
        if (configs != null) {
            this.specialKeyName = configs["custom.specialKey"].toString()
        }
    }

    override fun close() { }

    override fun partition(
        topic: String?,
        key: Any?,
        keyBytes: ByteArray?,
        value: Any?,
        valueBytes: ByteArray?,
        cluster: Cluster?
    ): Int {
        val partitionInfos = cluster!!.partitionsForTopic(topic)
        val numPartitions = partitionInfos.size
        val numSpecialPartitions = (numPartitions * 0.5).toInt()

        if (keyBytes == null) {
            return stickyPartitionCache.partition(topic, cluster)
        }

        if (key!!.equals(specialKeyName)) {
            return Utils.toPositive(Utils.murmur2(valueBytes)) % numSpecialPartitions
        }

        return Utils.toPositive(Utils.murmur2(keyBytes)) % (numPartitions - numSpecialPartitions) + numSpecialPartitions
    }

    companion object {
        private val logger = KotlinLogging.logger("CustomPartitioner")
    }
}