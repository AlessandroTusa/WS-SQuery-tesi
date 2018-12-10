public class Query {
    private String queryEseguita;
    private String testoMessaggio;
    private String SUGGERIMENTO;
    private int stato;

    public Query(String queryEseguita, String testoMessaggio, String SUGGERIMENTO, int stato) {
        this.queryEseguita = queryEseguita;
        this.testoMessaggio = testoMessaggio;
        this.SUGGERIMENTO = SUGGERIMENTO;
        this.stato = stato;
    }

    public void setQueryEseguita(String queryEseguita) {
        this.queryEseguita = queryEseguita;
    }

    public void setTestoMessaggio(String testoMessaggio) {
        this.testoMessaggio = testoMessaggio;
    }

    public void setSUGGERIMENTO(String SUGGERIMENTO) {
        this.SUGGERIMENTO = SUGGERIMENTO;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public String getQueryEseguita() {
        return queryEseguita;
    }

    public String getTestoMessaggio() {
        return testoMessaggio;
    }

    public String getSUGGERIMENTO() {
        return SUGGERIMENTO;
    }

    public int getStato() {
        return stato;
    }

    public String getQuery() {
        return queryEseguita;
    }


    @Override
    public String toString() {
        return queryEseguita +"-->"+ SUGGERIMENTO ;
    }


}
