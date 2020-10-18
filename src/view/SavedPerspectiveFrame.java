/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: 
 Bradley How
 Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: SavedPerspectiveFrame.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package view;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * JFrame that instantiate and adds the SavedperspectivePanel. Takes the list of
 * the memento names and passes it down to SavedperspectivePanel.
 *
 * CLASS TAKEN AND ADDAPTED FROM : https://bit.ly/3aPR8WU
 * 
 * @author Philippe Lepage
 * @see SavedPerspectivePanel
 */
public class SavedPerspectiveFrame extends JFrame {

	private SavedPerspectivePanel listPane;

	public SavedPerspectiveFrame(List<String> memento) {
		super("Load saved");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		listPane = new SavedPerspectivePanel(memento, this);
		listPane.setOpaque(true);
		this.setContentPane(listPane);

		this.pack();
		this.setVisible(true);
	}

	public String getToLoad() {
		return this.listPane.getToLoad();
	}

	public List<String> getToDelete() {
		return this.listPane.getToDelete();
	}
}
