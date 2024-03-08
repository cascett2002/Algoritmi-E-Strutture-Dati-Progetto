# Relazione sul Merge-BinaryInsertion Sort

## Sviluppo
Sviluppo di una libreria che include il metodo di ordinamento Merge-BinaryInsertion Sort, utilizzabile per ordinare vari tipi di dati.

**Sistema Operativo:** Ubuntu  
**Flag di compilazione gcc -Ofast:** Ottimizza significativamente la velocità di esecuzione del codice attivando una serie di ottimizzazioni avanzate.

## Analisi delle Prestazioni

Con l'aumentare di K oltre un certo limite significativo rispetto a zero (K >> 0), si osserva un marcato incremento nel tempo di esecuzione del programma. In dettaglio, la durata della computazione supera i 10 minuti per completarsi.

<table>
<tr>
<th>Risultati per Stringhe</th>
<th>Risultati per Int</th>
<th>Risultati per Float</th>
</tr>
<tr>
<td>

| k    | Tempo      |
|------|------------|
| 0    | 11.755111s |
| 1    | 12.109623s |
| 5    | 11.409674s |
| 10   | 11.302768s |
| 20   | 11.385192s |
| 50   | 12.005826s |
| 100  | 12.489219s |
| 150  | 12.060071s |
| 200  | 13.794531s |
| 350  | 15.250693s |
| 500  | 15.375724s |

</td>
<td>

| k    | Tempo      |
|------|------------|
| 0    | 6.163040s  |
| 1    | 6.240159s  |
| 5    | 6.253405s  |
| 10   | 5.748883s  |
| 20   | 5.830807s  |
| 50   | 6.073901s  |
| 100  | 6.583347s  |
| 150  | 6.423268s  |
| 200  | 7.601074s  |
| 350  | 9.837384s  |
| 500  | 9.623268s  |

</td>
<td>

| k    | Tempo      |
|------|------------|
| 0    | 6.553768s  |
| 1    | 6.359821s  |
| 5    | 6.036682s  |
| 10   | 6.015401s  |
| 20   | 6.241747s  |
| 50   | 6.418676s  |
| 100  | 6.839295s  |
| 150  | 6.823087s  |
| 200  | 7.861115s  |
| 350  | 9.818346s  |
| 500  | 10.248881s |

</td>
</tr>
</table>
