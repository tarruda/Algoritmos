package br.ufpe.cin.algoritmos.db;

import java.io.Serializable;
import java.util.List;

import br.ufpe.cin.algoritmos.binarytree.AVLTree;

class DatabaseIndex extends AVLTree<String, List<Integer>> implements
		Serializable {
	private static final long serialVersionUID = 5415847126134551628L;

}
