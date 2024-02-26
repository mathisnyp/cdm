// Use dynamic import for ESM modules
import('chalk').then(chalk => {
    const { readFile, writeFile, copyFile } = require('fs').promises;

    console.log(chalk.default.green('here'));

    function log(...args) {
        console.log(chalk.default.yellow('[react-native-maps]'), ...args);
    }

    async function reactNativeMaps() {
        log('📦 Creating web compatibility of react-native-maps using an empty module loaded on web builds');
        const modulePath = 'node_modules/react-native-maps';
        await writeFile(`${modulePath}/lib/index.web.js`, 'module.exports = {}', 'utf-8');
        await copyFile(`${modulePath}/lib/index.d.ts`, `${modulePath}/lib/index.web.d.ts`);
        const pkg = JSON.parse(await readFile(`${modulePath}/package.json`, 'utf-8'));
        pkg['react-native'] = 'lib/index.js';
        pkg['main'] = 'lib/index.web.js';
        await writeFile(`${modulePath}/package.json`, JSON.stringify(pkg, null, 2), 'utf-8');
        log('✅ script ran successfully');
    }

    reactNativeMaps().catch(console.error);
}).catch(console.error);
