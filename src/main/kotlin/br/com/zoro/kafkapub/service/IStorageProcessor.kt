package br.com.zoro.kafkapub.service

import com.amazonaws.services.s3.transfer.model.UploadResult

interface IStorageProcessor {
    fun senfFile():UploadResult
}