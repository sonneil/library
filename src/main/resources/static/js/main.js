$(document).ready(function() {
	var auxiliar = 0;
	
	$.ajax({
	  url: "/book",
	  context: document.body,
	  method: "GET"
	}).done(function(data) {
	  	fillBooksTable(data);
	});
	
	$.ajax({
	  url: "/reader",
	  context: document.body,
	  method: "GET"
	}).done(function(data) {
	  	fillReadersTable(data);
	});
	
	console.log("PRIMERO" + auxiliar);
});

function fillBooksTable(books) {
	for (var i=0;i<books.length;i++) {
		var book = books[i];
		$("#book_table tbody").append(
			'<tr>'+
			'<td><input type="text" value="'+book.title+'" />' +
			'</td><td><input type="text" value="'+book.type+'" />'+
			'</td><td><input type="text" value="'+book.editorial+'" />'+
			'</td><td><input type="text" value="'+book.year+'" />'+
			'</td><td><input type="button" value="Eliminar" onclick="deleteBook('+book.id+')" /></td>'+
			'</tr>'
		);
	}
}

function fillReadersTable(readers) {
	for (var i=0;i<readers.length;i++) {
		var reader = readers[i];
		
		/*<tr>
					<td><input type="text" value="LOTR" /></td>
					<td><input type="text" value="CF" /></td>
					<td><input type="text" value="T" /></td>
					<td>4,7,8,9</td>
					<td>Si</td>
					<td>
						<input type="text" value="" />
						<input type="button" value="Prestar" />
					</td>
					<td>
						<input type="text" value="" />
						<input type="button" value="Devolver" />
					</td>
				</tr>*/
		
		
		$("#reader_table tbody").append(
			'<tr>'+
			'<td><input type="text" value="'+reader.name+'" />' +
			'</td><td><input type="text" value="'+reader.phone+'" />'+
			'</td><td><input type="text" value="'+reader.address+'" />'+
			'</td><td><input id="borrow'+reader.id+'" type="text" value="" /><input type="button" value="Prestar" onclick="borrowBook('+reader.id+')" /></td>'+
			'</td><td><input id="borrowBack'+reader.id+'" type="text" value="" /><input type="button" value="Devolver" onclick="returnBook('+reader.id+')" /></td>'+
			'</tr>'
		);
	}
}

function createBook(id) {
	var book = {
		"title": $("#bookTitle").val(),
		"type":  1,
		"editorial":  $("#bookEditorial").val(),
		"year":  $("#bookYear").val()
	};
	
	$.ajax({
	  url: "/book?copies="+$("#bookCopies").val(),
	  contentType: "application/json",
	  data: JSON.stringify(book),
 	  dataType: "json",
	  type: "POST"
	}).done(function(data) {
	  	alert("Se ha create el libro: " + id);
		document. location. reload();
	});
}

function deleteBook(id) {
	$.ajax({
	  url: "/book/"+id,
	  contentType: "application/json; charset=utf-8",
	  type: "DELETE"
	}).done(function(data) {
	  	alert("Se ha eliminado el libro: " + id);
		document. location. reload();
	});
}

function borrowBook(readerId) {
	var copy = {
		"id":$("borrow"+readerId).val()
	}
	
	$.ajax({
	  url: "/reader/"+readerId+"/borrow",
	  contentType: "application/json",
	  data: JSON.stringify(copy),
 	  dataType: "json",
	  type: "POST"
	}).success(function(data) {
	  	alert("Se ha devuelto la copia: " + copy.id);
		document. location. reload();
	});
}

function returnBook(readerId) {
	var copy = {
		"id":$("borrowBack"+readerId).val()
	}
	
	$.ajax({
	  url: "/reader/"+readerId+"/borrow/back",
	  contentType: "application/json",
	  data: JSON.stringify(copy),
 	  dataType: "json",
	  type: "POST"
	}).done(function(data) {
	  	alert("Se ha devuelto la copia: " + copy.id);
		document. location. reload();
	});
}