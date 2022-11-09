aws sqs send-message \
  --endpoint-url=http://localhost:4566 \
  --queue-url http://localhost:4566/000000000000/publisher-queue \
  --message-body file://transaction2.json \
  --region=us-east-1

