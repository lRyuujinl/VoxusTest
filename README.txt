Dificuldades:

1. Usar um padrão de projeto mais eficiente para receber o input do usuario de forma simples.
2. Alguns impasses menores para achar uma maneira de flexibilizar a entrada de dados para as funções do banco de dados.
3. Não estou acostumado a usar o modelo MVC
4. Integrar a interface com o banco de dados.

Facilidades:
1. Montar o banco de dados, uma vez que treinei intensamente essa área.
2. Conectar com o banco de dados.

Observações importantes:
!!!: Por mais que pareça que arquitetura MVC esteja aplicada, na verdade não está. Algumas funções de Controller e Model, por exemplo, serão encontradas na classe View. Isso se deu pela minha inexperiência ainda com essa arquitetura.
1. Poucas partes do codigo estão hardcodadas, vulgo não são escaláveis, mas dado o prazo e o tempo disponível, a prioridade é fazer funcionar e depois otimizar.
2. Há varíos tratamentos de erros e exceções que devem ser revisadas, mas pela falta de tempo, não foi possível revisar todas.
3. O banco de dados está conectado com o PgAdmin(PostgreSQL SGDB) mas a conexão foi local (Como pode ser verificado no parametro URL na classe DBOperations.java).
4. Alguns padrões de projeto foram aplicados, como o Facade, porém não estão otimizadas.