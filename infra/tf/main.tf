provider "aws" {
  region      = "us-east-1"
  access_key = "ASHUDSAUDHASUDHASD"
  secret_key = "AHUSDHAUDHAUSDHAUHDSA"
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true
 // s3_force_path_style         = true
  s3_use_path_style           = true

  endpoints {
    dynamodb = "http://localhost:4566"
    sqs   = "http://localhost:4566"
    sns  = "http://localhost:4566"
    s3  = "http://localhost:4566"
  }
}

//--------------------------------------------- SQS
resource "aws_sqs_queue" "publisher-queue-dlq" {
  name                      = "publisher-queue-dlq"
  delay_seconds             = 90
  max_message_size          = 2048
  message_retention_seconds = 86400
  receive_wait_time_seconds = 10
}

resource "aws_sqs_queue" "publisher-queue" {
  name                      = "publisher-queue"
  delay_seconds             = 90
  max_message_size          = 2048
  message_retention_seconds = 86400
  receive_wait_time_seconds = 10
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.publisher-queue-dlq.arn
    maxReceiveCount     = 4
  })
}

//------------------------------------------------------- SNS
resource "aws_sns_topic" "topic-distribuition" {
  name = "topic-distribuition"
}

//--------------------------------------------------------SUBS SQS to SNS
resource "aws_sns_topic_subscription" "topic_to_sqs_target" {
  topic_arn = aws_sns_topic.topic-distribuition.arn
  protocol  = "sqs"
  endpoint  = aws_sqs_queue.publisher-queue.arn
}


//-------------------------------------------------------- DYNAMODB TABLES
resource "aws_dynamodb_table" "tb_processing" {
  name           = "tb001-publisher"
  read_capacity  = "20"
  write_capacity = "20"
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }
}
//-------------------------------------------------------- s3 Bucket ->  payment-bucket
resource "aws_s3_bucket" "payment-bucket" {
  bucket = "payment-bucket"
}

resource "aws_s3_bucket_acl" "paymento_bucket_acl" {
  bucket = aws_s3_bucket.payment-bucket.id
  acl    = "public-read-write"
}