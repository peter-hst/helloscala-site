package com.helloscala.site.data.domain

import java.time.LocalDateTime

case class FileUploadResult(id: String,
                            sha: String,
                            filename: String,
                            mimetype: String,
                            size: Long,
                            uri: String,
                            host: Option[String],
                            createdAt: LocalDateTime)
