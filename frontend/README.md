# Code explaination


## Structure

app
	modal.tsx <--- localhost:3000/modal
	items
		item1.tsx <-- localhost:3000/items/item1
		item2.tsx ...
	(cart)
		_layout.tsx <-- overrides any other _layout.tsx file that is a level above this on in the file structure (layout for all pages on this level and levels bellow)
		cart1.tsx <-- localhost:3000/cart1
		carts2.tsx ...
	lib <-- contains library code for example to call extenal api endpoints
	[...missing].tsc <-- localhost:3000/<any_path_that_doesnt_exist>
	_layout.tsx	 <-- layout for every single page
	+html.tsx	 <-- html boilperplate for all pages
	
constants <-- contains constants like colours etc. for the whole project
components <-- contains components that the rest of the website can be built of
	component1
	component2
	...


## Html page Structure:


	<+html>
		<_layout.tsx> <-- either root level file or one that has overwritten it
			<page_file.tsx> (item1, item2, missing, ....)
				<components>..... (component1, component 2...) components could also contain other components like:
				<component1>
					<component2/>
					<component3/>
				</component1>
			</page_file.tsx>
		</_layout.tsx>
	</+html>