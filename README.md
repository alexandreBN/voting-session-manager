# Voting Session Manager

Projeto criado para fazer o gerenciamento de sessões de votação com base nos votos efetuados pelos associados de uma organização em determinada pauta.

## Pré-requisitos
* Ambiente com Java 8+
* MySQL
* Maven

## Execução do Projeto
  ``` git clone https://github.com/alexandreBN/voting-session-manager.git```

  ``` cd voting-session-manager```

  - No Docker
    - ```cd docker```
    - ```docker-compose up --build```
  
  - No terminal
    - ```mvn clean install -U -Dmaven.test.skip=true```
    - ```java -jar voting-session/target/voting-session-0.0.1-SNAPSHOT.jar```

  - No Eclipse
    - Importe o projeto
    - Execute o comando ```mvn clean install -U -Dmaven.test.skip=true``` no projeto raiz
    - Execute o ```Update Projet``` do maven

Após a execução dos comandos acima a aplicação estará disponível na porta **8010**.

## Execução dos Testes Automatizados
Para executar todos os testes automatizados é necessário executar a classe ```VotingSessionSuite```

## Documentação

Para fazer com que uma pauta seja pelos votada pelos associados em uma determinada sessão de votação é necessário:
- Cadastrar uma Pauta
- Abrir uma Sessão de Votação para a Pauta criada
- Efetuar a votação de modo a identificar o associado que está votando
  - Os votos dos associados podem ser feitos:
    - Utilizando a base de dados interna de associados (onde devem ser cadastrados os associados)
    - Utilizando o endpoint referente a API externa integrada ao sistema (no qual deve ser informado o CPF do associado)
- Após a Sessão de Votação ser finalizada não é possível efetuar mais votos e é possível ver os resultados ou acompanhar a votação até a mesma ser finalizada.

### Documentação da API via SWAGGER

A documentação da API está disponível através do path ```/swagger-ui.html```.

Para auxiliar a execução das requisições foi disponibilizado o arquivo `Voting Session.postman_collection.json` para ser utilizado no Postman.

## Tarefas Bônus

- [x] Tarefa Bônus 1 - Integração com sistemas externos
- [ ] Tarefa Bônus 2 - Mensageria e filas
- [x] Tarefa Bônus 3 - Performance
  - Para performance foi criado um projeto (```Voting Session.jmx```) no JMeter de modo a 1) Criar uma Pauta, 2) Abrir uma Sessão de Votação para Pauta e 3) Fazer com que uma lista de 10 mil associados efetuassem seus votos nessa pauta. É importante ressaltar que para a emissão das requisições referentes aos associados é necessário importar, na thread referente aos votos o arquivo ```Voting Session.csv```.
- [x] Tarefa Bônus 4 - Versionamento da API 
  - Versionamento da API utilizando alguma plataforma de hospedagem de código que utiliza o Git, como GitHub, GitLab, entre outros. 
    A estratégia a ser utilizada seria a baseada no GitFlow, isto é, 1) Dividir o ambiente do projeto em 2 branchs padrões, sendo development e master, 2) Criar tarefas (issues) a serem implementadas, 3) Efetuar um fork do repositório, 4) Criar uma branch para resolver uma tarefa (issue) específica, 5) Enviar um Pull Request para a branch development e 5) Após feito o merge fechar a versão efetuando um Pull Request da branch development para a branch master*