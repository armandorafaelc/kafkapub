aws sqs send-message \
  --cli-input-json file://transaction.json \
  --endpoint-url http://localhost:4566 \
  --region us-east-1