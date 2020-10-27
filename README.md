
<h2 align="center">
	Apriori Algorithm
</h2>


#### Mining and Association rules based on relational data 
  
A Java project combining relational database and [Weka machine learning platform](https://www.cs.waikato.ac.nz/ml/weka/).
Based on transactional data the algorithm structures and navigate a Hash tree in order to insight over correlated items.

It's intelligence is based on some measures (statistic & aggregation-like):
- **Support threshold**: signifies the popularity of an item on dataset (number of transactions in which X appears / total of transactions);
- **Confidence**: measures the likely of an especific item appear when another one is present (_Condition Probability_ highly biased);
- **Lift**: signifies the likelyhood of an item being present when another is, considering both popularity (support); 
- **Conviction**: tests and measures accidental chance of association, questioning an immediate correlation between items (i.e. frequently purchased together, so...);

Although applied on bundle creation - as a product lifecycle approach and strategic planning tool - due to a poor overall performance the algorithm runs under constraint, requiring a manual/analytical handling balancing the subset quantities and decision accument over well-known assumptions.

_(I love Weka and NZ!!!)_
  
  
