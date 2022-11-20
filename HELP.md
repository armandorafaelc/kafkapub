# Kafka Pub - Project

<img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" />


Este projeto tem por finalidade, estudar tecnologias emergentes.

Ela consistem em receber um 'Pagamento', via AWS SQS, e processar este pagamento atraves de Kafka e persistindo
em um banco de dados DynamoDB.




### Subindo o ambiente - Localstack
Para conseguir executar o ambiente com sucesso, você deverá possuir 
os seguintes programas instalados em sua maquina:

| Programa                                                                                                       | Link para Download                     |
|----------------------------------------------------------------------------------------------------------------|----------------------------------------|
| <img src="https://img.shields.io/badge/Terraform-7B42BC?style=for-the-badge&logo=terraform&logoColor=white" /> |  [Terraform](https://www.terraform.io/)     |
| <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />       | [Docker](https://www.docker.com/) |  

Após estes programas instalados, poderemos prosseguir com os seguintes passos:

1. Suba o Docker.
2. Execute o comando, na seguinte pasta /infra: `docker compose up . `.
3. Acesse a pasta tf.
4. Execute o arquivo start_tf.sh
5. Digite: YES
6. Aguarde o final do processamento e criação da infra.

### Executando um teste Local

Após a infra instalada, poderemos prosseguir com os seguinte teste local:

1. Acessar a pasta /infra/scripts.
2. Execute o arquivo, `transactionQueue.sh`.
3. Caso ocorra, com sucesso, o processo terminará com uma mensagem semelhante a esta:
```
{
    "MD5OfMessageBody": "7b5a5d70933e88c362f2acb2967a49bb",
    "MessageId": "37617ad2-d5d5-41c2-bf7f-f99cec9f2853"
}
```


### Setando o seguinte profile
``
-Dspring.profiles.active=localstack
``